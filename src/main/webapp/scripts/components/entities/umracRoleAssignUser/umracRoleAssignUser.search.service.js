'use strict';

angular.module('stepApp')
    .factory('UmracRoleAssignUserSearch', function ($resource) {
        return $resource('api/_search/umracRoleAssignUsers/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
