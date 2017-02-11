'use strict';

angular.module('stepApp')
    .factory('SkillSearch', function ($resource) {
        return $resource('api/_search/skills/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    })
    .factory('SkillEmployee', function ($resource) {
        return $resource('api/skills/employee/:id', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
