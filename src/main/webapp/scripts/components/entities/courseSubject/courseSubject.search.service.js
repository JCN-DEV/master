'use strict';

angular.module('stepApp')
    .factory('CourseSubjectSearch', function ($resource) {
        return $resource('api/_search/courseSubjects/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
