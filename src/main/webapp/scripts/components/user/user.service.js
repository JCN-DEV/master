'use strict';

angular.module('stepApp')
    .factory('User', function ($resource) {
        return $resource('api/users/:login', {}, {
                'query': {method: 'GET', isArray: true},
                'get': {
                    method: 'GET',
                    isArray:false,
                    transformResponse: function (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                },
                'update': { method:'PUT' }
            });
        }).factory('UserCustomUpdate', function ($resource) {
        return $resource('api/users/customUpdate', {}, {
                'update': { method:'PUT' }
            });
        }).factory('UserBlocked', function ($resource) {
        return $resource('api/blockusers', {}, {
            'query': {method: 'GET', isArray: true}
            /*,
            'get': {
                method: 'GET',
                isArray:false,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }*/
        });
    });
