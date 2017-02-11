'use strict';

angular.module('stepApp')
    .factory('InstituteLabInfo', function ($resource, DateUtils) {
        return $resource('api/instituteLabInfos/:id', {}, {
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
