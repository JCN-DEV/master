'use strict';

angular.module('stepApp')
    .factory('InstituteGovernBody', function ($resource, DateUtils) {
        return $resource('api/instituteGovernBodys/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
