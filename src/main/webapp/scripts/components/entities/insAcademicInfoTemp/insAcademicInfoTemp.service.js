'use strict';

angular.module('stepApp')
    .factory('InsAcademicInfoTemp', function ($resource, DateUtils) {
        return $resource('api/insAcademicInfoTemps/:id', {}, {
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
