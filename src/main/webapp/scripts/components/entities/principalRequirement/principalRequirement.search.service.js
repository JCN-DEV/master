'use strict';

angular.module('stepApp')
    .factory('PrincipalRequirementSearch', function ($resource) {
        return $resource('api/_search/principalRequirements/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
