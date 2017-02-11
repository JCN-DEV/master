'use strict';

angular.module('stepApp')
    .factory('UmracIdentitySetupSearch', function ($resource) {
        return $resource('api/_search/umracIdentitySetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
