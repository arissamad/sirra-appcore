sirra-appcore
=============

Sirra App Core - Provides services such as SQL, emailing, daily process, and menus.

Changelog
---------
2014-05-10: Moved to appcore:
/api/users/BaseUsers (your Users.java can extend this file)
/api/users/BaseSignUp (You should do your own SignUp extending this file)
/api/users/ResetPassword
/api/users/CheckResetHash
/api/users/Self
/api/users/Archive
/api/users/UploadPhoto
AppDetails (Your HerokuStarter should define the required attributes)

BaseUser added fields: thumbUrl and archived

You can remove EmailValidator dependency from your project. It is now in AppCore.

/api/menus/Menus (You can remove your Menus.java)