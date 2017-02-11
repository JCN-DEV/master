'use strict';

angular.module('stepApp')
    .factory('JpSkillSearch', function ($resource) {
        return $resource('api/_search/jpSkills/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('ActiveJpSkill', function ($resource) {
        return $resource('api/jpSkills/active', {}, {
            'query': { method: 'GET', isArray: true}
        });
    }).factory('JpSkillByName', function ($resource) {
        return $resource('api/jpSkills/byName/:name', {}, {
            'query': { method: 'GET', isArray: false}
        });
    });
