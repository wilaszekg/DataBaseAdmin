import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "databaseadmin"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "org.springframework" % "spring-core" % "3.2.2.RELEASE",
    "org.springframework" % "spring-aop" % "3.2.2.RELEASE",
    "org.springframework" % "spring-asm" % "3.1.4.RELEASE",
    "org.springframework" % "spring-beans" % "3.2.2.RELEASE",
    "org.springframework" % "spring-context" % "3.2.2.RELEASE",
    "org.springframework" % "spring-expression" % "3.2.2.RELEASE",
    "org.springframework" % "spring-tx" % "3.2.2.RELEASE",
    "org.springframework.security" % "spring-security-ldap" % "3.1.3.RELEASE",
    "be.objectify" %% "deadbolt-java" % "2.0-SNAPSHOT"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    resolvers += DefaultMavenRepository,
    resolvers += Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
    resolvers += Resolver.url("Objectify Play Snapshot Repository", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns),
    resolvers += "MVNrepositories" at "http://mvnrepository.com/artifact/"


  )

}
