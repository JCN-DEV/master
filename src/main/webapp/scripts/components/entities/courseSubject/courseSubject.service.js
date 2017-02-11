'use strict';

angular.module('stepApp')
    .factory('CourseSubject', function ($resource, DateUtils) {
        return $resource('api/courseSubjects/:id', {}, {
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
