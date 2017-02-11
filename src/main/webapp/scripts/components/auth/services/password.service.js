'use strict';

angular.module('stepApp')
    .factory('Password', function ($resource) {
        return $resource('api/account/change_password', {}, {
        });
    });

angular.module('stepApp')
    .factory('PasswordResetInit', function ($resource) {
        return $resource('api/account/reset_password/init', {}, {
        })
    });

angular.module('stepApp')
    .factory('PasswordResetFinish', function ($resource) {
        return $resource('api/account/reset_password/finish', {}, {
        })
    });
angular.module('stepApp')
    .factory('PasswordCheck', function ($resource) {
        return $resource('/account/checkPassword/{pass}', {}, {
        })
    });
