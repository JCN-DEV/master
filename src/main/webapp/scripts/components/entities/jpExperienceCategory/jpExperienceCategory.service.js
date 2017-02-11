'use strict';

angular.module('stepApp')
    .factory('JpExperienceCategory', function ($resource, DateUtils) {
        return $resource('api/jpExperienceCategorys/:id', {}, {
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
