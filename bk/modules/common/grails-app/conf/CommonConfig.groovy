commonConfig {

    cloudinary.config = [
            cloud_name     : "famelive",
            api_key        : "195657926447579",
            api_secret     : "mWBJAvhhbKWPCcB-R3dmKXVOdTA",
            folder         : 'dev',
            mimeType       : 'png',
            userImageSize  : 500,
            eventPosterSize: 5000
    ]

    environments {
        development {
            cloudinary.config['folder'] = 'dev'
        }
        qa {
            cloudinary.config['folder'] = 'qa'
        }
        production {
            cloudinary.config['folder'] = 'prod'
        }

    }

    cloudinary.config['baseUrl'] = "http://res.cloudinary.com/${cloudinary.config['cloud_name']}/image/upload/}"

    concurrent.channels = 5

}

rabbitmq {
    connectionfactory {
        username = 'guest'
        password = 'guest'
        hostname = 'localhost'
        concurrentConsumers=50
    }
    queues = {
        FAMELIVE_QUEUE()
    }
    services {
        rabbitMQMessageReceiverService {
            concurrentConsumers = 50
            disableListening = false
        }
    }
}