'use strict';

angular.module('stepApp')
    .factory('InstEmpAddressTemp', function ($resource, DateUtils) {
        return $resource('api/instEmpAddressTemps/:id', {}, {
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
