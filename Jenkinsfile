@Library('jenkins-pipeline-utils') _

def notifyBuild(String buildStatus, Exception e) {
  buildStatus =  buildStatus ?: 'SUCCESSFUL'

  // Default values
  def colorName = 'RED'
  def colorCode = '#FF0000'
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = """*${buildStatus}*: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':\nMore detail in console output at <${env.BUILD_URL}|${env.BUILD_URL}>"""
  def details = """${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':\n
    Check console output at ${env.BUILD_URL} """
  // Override default values based on build status
  if (buildStatus == 'STARTED') {
    color = 'YELLOW'
    colorCode = '#FFFF00'
  } else if (buildStatus == 'SUCCESSFUL') {
    color = 'GREEN'
    colorCode = '#00FF00'
    summary = "${pipelineStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' in stage '${curStage}' for branch '${branch}' (${env.BUILD_URL})"
  } else {
    color = 'RED'
    colorCode = '#FF0000'
    details +="<p>Error message ${e.message}, stacktrace: ${e}</p>"
    summary +="\nError message ${e.message}, stacktrace: ${e}"
  }
  //
  //slackSend channel: "#tech-intake", baseUrl: 'https://hooks.slack.com/services/', tokenCredentialId: 'slackmessagetpt2', color: colorCode, message: summary
  emailext(
      subject: subject,
      body: details,
      attachLog: true,
      recipientProviders: [[$class: 'DevelopersRecipientProvider']],
      to: "tom.parker@osi.ca.gov, adarsh.vandana@osi.ca.gov"
    )
}

node ('tpt4-slave'){
   def serverArti = Artifactory.server 'CWDS_DEV'
   def rtGradle = Artifactory.newGradleBuild()
   def docker_credentials_id = '6ba8d05c-ca13-4818-8329-15d41a089ec0'
   def github_credentials_id = '433ac100-b3c2-4519-b4d6-207c029a103b'
   newTag = '';
   try {
   stage('Preparation') {
		  git branch: '$branch', credentialsId: '433ac100-b3c2-4519-b4d6-207c029a103b', url: 'git@github.com:ca-cwds/API.git'
		  rtGradle.tool = "Gradle_35"
		  rtGradle.resolver repo:'repo', server: serverArti
		  rtGradle.useWrapper = false
   }
   
   stage('Increment Tag'){
       newTag = newSemVer()
   }

   stage('Build'){
       def buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: "jar -D build=${BUILD_NUMBER} -DnewVersion=${newTag}".toString()
   }

   stage('Tests') {
       buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'test jacocoTestReport javadoc', switches: "--stacktrace -D build=${BUILD_NUMBER} -DnewVersion=${newTag}".toString()
       publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/tests/test', reportFiles: 'index.html', reportName: 'JUnit Report', reportTitles: 'JUnit tests summary'])
   }
   stage('Integration Tests'){
         withEnv(['APP_STD_PORT=8088']) {
            buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'integrationTest  jacocoTestReport', switches: "--stacktrace -D build=${BUILD_NUMBER} -DnewVersion=${newTag}".toString()
            publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/tests/integrationTest', reportFiles: 'index.html', reportName: 'IT Report', reportTitles: 'Integration Tests summary'])
       }
	}
   stage('SonarQube analysis'){
		withSonarQubeEnv('Core-SonarQube') {
			buildInfo = rtGradle.run buildFile: 'build.gradle', switches: "--info  -D build=${BUILD_NUMBER} -DnewVersion=${newTag}".toString(), tasks: 'sonarqube'
        }
    }
	
	stage ('Push to artifactory'){
      //rtGradle.deployer repo:'libs-snapshot', server: serverArti
	    rtGradle.deployer repo:'libs-release', server: serverArti
	    rtGradle.deployer.deployArtifacts = true
		//buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: 'artifactoryPublish'
		buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: "publish -D build=${BUILD_NUMBER} -DnewVersion=${newTag}".toString()
		rtGradle.deployer.deployArtifacts = false
	}
	stage ('Build Docker'){
	   withDockerRegistry([credentialsId: docker_credentials_id]) {
           buildInfo = rtGradle.run buildFile: 'build.gradle', tasks: "publishDocker -D build=${BUILD_NUMBER} -DnewVersion=${newTag}".toString()
       }
	}
	
    stage('Tag Git') {
        def buildNumber = "${env.BUILD_NUMBER}"
        tagGithubRepo(newTag +"."+ buildNumber, github_credentials_id)
    }
    
	stage('Clean Workspace') {
		cleanWs()
	}
	
	stage('Deploy Application') {
		build job: 'tpt4-api-deploy-app', parameters: [string(name: 'version', value: 'latest'), string(name: 'inventory', value: 'inventories/development/hosts.yml')], propagate: false
	}
 } catch (Exception e)    {
 	   errorcode = e
  	   currentBuild.result = "FAIL"
  	   notifyBuild(currentBuild.result,errorcode)
  	   throw e;
 }finally {
       publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/tests/test', reportFiles: 'index.html', reportName: 'JUnit Report', reportTitles: 'JUnit tests summary'])
       publishHTML([allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'build/reports/tests/integrationTest', reportFiles: 'index.html', reportName: 'IT Report', reportTitles: 'Integration Tests summary'])
       cleanWs()
 }
 
}

