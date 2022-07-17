package com.book.manager.bookmanager.presentation.app

import com.book.manager.bookmanager.application.service.security.BookManagerUserDetails
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.*
import org.slf4j.LoggerFactory
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

//ロガー（ログを生成する）
private val logger = LoggerFactory.getLogger(LoggingAdvice::class.java)

@Aspect
@Component
class LoggingAdvice {
    @Before("execution(* com.book.manager.bookmanager.presentation.controller..*.*(..))")
    //JoinPointはBefore,Afterが呼び出される対象の処理（ここではControllerクラスの処理）の情報が含まれている
    fun beforeLog(joinPoint: JoinPoint) {
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        logger.info("Start: ${joinPoint.signature} userId:${user.id}")
        logger.info("Class: ${joinPoint.target.javaClass}")
        logger.info("Session: ${(RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.session.id}")
    }
    @After("execution(* com.book.manager.bookmanager.presentation.controller..*.*(..))")
    fun afterLog(joinPoint: JoinPoint){
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        logger.info("End: ${joinPoint.signature} userId=${user.id}")
    }
    @Around("execution(* com.book.manager.bookmanager.presentation.controller..*.*(..))")
    fun aroundLog(joinPoint: MethodInvocationProceedingJoinPoint):Any?{
        //前処理
        val user = SecurityContextHolder.getContext().authentication.principal as BookManagerUserDetails
        logger.info("Start: ${joinPoint.signature} userId:${user.id}")
        //本処理の実行
        val result  = joinPoint.proceed()
        //後処理
        logger.info("En Proceed  ${joinPoint.signature} userId=${user.id}")
        //本処理の結果返却
        return result
    }
    //指定した名前で対象処理の戻り値を扱うことができる
    @AfterReturning("execution(* com.book.manager.bookmanager.presentation.controller..*.*(..))", returning = "returnValue")
    fun afterReturningLog(joinPoint: JoinPoint,returnValue:Any?){
        logger.info("End ${joinPoint.signature} returnValue=${returnValue}")
    }
    @AfterThrowing("execution(* com.book.manager.bookmanager.presentation.controller..*.*(..))", throwing = "e")
    fun afterThrowingLog(joinPoint: JoinPoint,e:Throwable){
        logger.error("Exception: ${e.javaClass} signature=${joinPoint.signature} message=${e.message}")
    }
}