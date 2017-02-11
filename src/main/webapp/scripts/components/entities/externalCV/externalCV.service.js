'use strict';

angular.module('stepApp')
    .factory('ExternalCV', function ($resource, DateUtils) {
        return $resource('api/externalCVs/:id', {}, {
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
