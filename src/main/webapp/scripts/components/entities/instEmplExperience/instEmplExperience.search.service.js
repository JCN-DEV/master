'use strict';

angular.module('stepApp')
    .factory('InstEmplExperienceSearch', function ($resource) {
        return $resource('api/_search/instEmplExperiences/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('InstEmplExperienceCurrent', function ($resource) {
        return $resource('api/instEmplExperiencesCurrent', {}, {
            'get': { method: 'GET', isArray: true}
        });
    });
