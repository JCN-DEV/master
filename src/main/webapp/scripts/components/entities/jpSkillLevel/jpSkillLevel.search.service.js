'use strict';

angular.module('stepApp')
    .factory('JpSkillLevelSearch', function ($resource) {
        return $resource('api/_search/jpSkillLevels/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ActiveJpSkillLevel', function ($resource) {
        return $resource('api/jpSkillLevels/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
