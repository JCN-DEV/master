'use strict';

angular.module('stepApp')
    .factory('InstShopInfo', function ($resource, DateUtils) {
        return $resource('api/instShopInfos/:id', {}, {
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
