'use strict';

angular.module('stepApp')
    .factory('InstShopInfoTemp', function ($resource, DateUtils) {
        return $resource('api/instShopInfoTemps/:id', {}, {
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
