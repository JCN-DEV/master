'use strict';

angular.module('stepApp')
    .factory('UmracModuleSetupSearch', function ($resource) {
        return $resource('api/_search/umracModuleSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
