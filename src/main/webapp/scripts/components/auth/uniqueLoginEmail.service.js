'use strict';

angular.module('stepApp')
    .factory('CheckLogin', function ($resource, DateUtils) {
        return $resource('api/account/checkLogin/:login', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    })
    .factory('CheckEmail', function ($resource, DateUtils) {
        return $resource('api/account/checkLogin/:email', {}, {
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            }
        });
    });
