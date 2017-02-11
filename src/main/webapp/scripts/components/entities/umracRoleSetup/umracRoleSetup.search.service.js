'use strict';

angular.module('stepApp')
    .factory('UmracRoleSetupSearch', function ($resource) {
        return $resource('api/_search/umracRoleSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
