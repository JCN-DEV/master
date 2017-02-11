'use strict';

angular.module('stepApp')
    .factory('CourseSubSearch', function ($resource) {
        return $resource('api/_search/courseSubs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
