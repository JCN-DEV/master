'use strict';

angular.module('stepApp')
    .factory('CourseTechSearch', function ($resource) {
        return $resource('api/_search/courseTechs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
