'use strict';

angular.module('stepApp')
    .factory('JpEmployeeExperienceSearch', function ($resource) {
        return $resource('api/_search/jpEmployeeExperiences/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpEmployeeExperienceJpEmployee', function ($resource) {
        return $resource('api/jpEmployeeExperiences/jpEmployee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
