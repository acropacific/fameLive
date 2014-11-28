// Place your Spring DSL code here
beans = {

    rabbitMQMessageReceiverService_MessageListenerContainer {
        channelTransacted = false
        concurrentConsumers = 50
        prefetchCount = 5
        queueNames = ["FAMELIVE_QUEUE"] as String[]
    }
}