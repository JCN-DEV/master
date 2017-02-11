'use strict';

angular.module('stepApp')
    .factory('CourseTech', function ($resource, DateUtils) {
        return $resource('api/courseTechs/:id', {}, {
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
