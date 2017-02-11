'use strict';

angular.module('stepApp')
    .factory('TempEmployer', function ($resource, DateUtils) {
        return $resource('api/tempEmployers/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data.length > 0) {
                        data = angular.fromJson(data);
                        return data;
                    } else {
                        return {};
                    }
                }
            },
            'update': { method:'PUT' }
        });
    });
