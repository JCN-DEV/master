'use strict';

angular.module('stepApp')
    .factory('CmsSubAssign', function ($resource, DateUtils) {
        return $resource('api/cmsSubAssigns/:id', {}, {
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
