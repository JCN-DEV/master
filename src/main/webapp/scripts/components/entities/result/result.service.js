'use strict';

angular.module('stepApp')
    .factory('Result', function ($resource, DateUtils) {
        return $resource('api/results/:id', {}, {
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
