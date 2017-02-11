'use strict';

angular.module('stepApp')
    .factory('ExperienceSearch', function ($resource) {
        return $resource('api/_search/experiences/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('ExperienceEmployee', function ($resource) {
        return $resource('api/experiences/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
