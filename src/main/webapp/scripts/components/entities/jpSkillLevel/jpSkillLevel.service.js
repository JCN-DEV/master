'use strict';

angular.module('stepApp')
    .factory('JpSkillLevel', function ($resource, DateUtils) {
        return $resource('api/jpSkillLevels/:id', {}, {
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
