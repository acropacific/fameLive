# .bashrc
. ~/.userSettings
# Source global definitions
if [ -f /etc/bashrc ]; then
	. /etc/bashrc
fi

# User specific aliases and functions

export JAVA_HOME="/home/ec2-user/java/java-7-oracle"
export PATH=$PATH:$JAVA_HOME/bin

#THIS MUST BE AT THE END OF THE FILE FOR GVM TO WORK!!!
[[ -s "/home/ec2-user/.gvm/bin/gvm-init.sh" ]] && source "/home/ec2-user/.gvm/bin/gvm-init.sh"
