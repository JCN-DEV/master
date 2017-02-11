'use strict';

angular.module('stepApp')
    .factory('CmsTrade', function ($resource, DateUtils) {
        return $resource('api/cmsTrades/:id', {}, {
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
