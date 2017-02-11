'use strict';

angular.module('stepApp')
    .factory('InstLabInfo', function ($resource, DateUtils) {
        return $resource('api/instLabInfos/:id', {}, {
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
