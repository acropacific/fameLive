package com.famelive.admin

import com.cloudinary.Cloudinary

class AdminCloudinaryService {

    def grailsApplication

    static transactional = false

    Map uploadImage(Object bytesOrString, String imageName, List<String> tags) throws Exception {
        def cloudinaryConfig = grailsApplication.config.cloudinary.config
        if ((bytesOrString instanceof byte[]) || (bytesOrString instanceof String)) {
            try {
                String commaSeparatedTags = tags.collect {
                    replaceSpaces(it)
                }.join(',')
                String publicId = imageName
                return client.uploader().upload(bytesOrString, [
                        'public_id': publicId,
                        'tags'     : commaSeparatedTags,
                        'folder'   : cloudinaryConfig['folder']
                ])

            } catch (Exception e) {
                log.debug("file not found")
            }
        } else {
            throw new Exception('data not accepted. Use either byte[] or String URL.')
        }
    }

    String replaceSpaces(String name) {
        return name.replaceAll(' ', '-')
    }

    Cloudinary getClient() {
        new Cloudinary(cloudinaryConfig)
    }

    Map getCloudinaryConfig() {
        grailsApplication.config.cloudinary.config as Map
    }

    Map deleteByUserName(String userName) {
        String publicId = cloudinaryConfig.folder + "/" + userName
        return client.uploader().destroy(publicId, [:])
    }
}
