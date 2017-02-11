'use strict';

angular.module('stepApp')
    .factory('InstPlayGroundInfo', function ($resource, DateUtils) {
        return $resource('api/instPlayGroundInfos/:id', {}, {
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
