package com.famelive.common.user

class UserInfo {
    static belongsTo = [user: User]

    String firstName
    String lastName
    String middleName
    String location
    String gender
    Date dob

    static constraints = {
        firstName nullable: true
        lastName nullable: true
        middleName nullable: true
        location nullable: true
        gender nullable: true
        dob nullable: true
    }
}
