set -e
EXPECTED_ARGS=2
FILE_PRE="./preDeploy.sh"
FILE_POST="./postDeploy.sh"
if [ -e $FILE_PRE ];
then
	echo "Now calling pre deployment script"
	$FILE_PRE
fi
cd $GRAILS_PROJECT_HOME
ARG1=$1
ARG2=$2
REV="--rev"
BRANCH="--branch"
LOCAL_CHANGES="--localChanges"
ENV="--env"
USE_CHECKOUT=1
echo $ARG1
echo $ARG2
if [ "$ARG1" == "$LOCAL_CHANGES" ]; then
	USE_CHECKOUT=2
fi
if [ "$USE_CHECKOUT" != "2"  ]; then
	git reset --hard
fi
if [ $# == 2 ]; then
    echo "In if"
    if  [ $ARG1 == "$BRANCH" ]; then
		echo "In BRANCH"
        USE_CHECKOUT=0
		git checkout $ARG2
		git pull origin $ARG2
    elif [ $ARG1 == "$REV" ]; then
		git checkout $ARG2
        USE_CHECKOUT=0
    fi
fi
echo $USE_CHECKOUT
if [ "$USE_CHECKOUT" == "1"  ]; then
    echo "Pulling from origin master"
	git checkout master
	git pull origin master
fi
grails clean;
JASPER_INSTALLED=`cat $GRAILS_PROJECT_HOME/application.properties | grep -y "jasper" | wc -l`
DEPLOY_ENV=$GRAILS_ENV
if [ "$ARG1" == "$ENV" ]; then
	DEPLOY_ENV=$ARG2
fi
if [ $JASPER_INSTALLED != "0" ]; then
    rm -rf ./web-app/reports/*.jasper
    grails compile-reports
fi
grails -Dgrails.env=$DEPLOY_ENV war --non-interactive ~/tomcat/apache-tomcat-7.0.55/webapps/ROOT.war
kill -9 `cat $CATALINA_PID`;
rm -rf ~/tomcat/apache-tomcat-7.0.55/webapps/ROOT
rm -rf $CATALINA_PID
cd
if [ -e $FILE_POST ];
then
	echo "Now calling post deployment script"
	$FILE_POST
fi

