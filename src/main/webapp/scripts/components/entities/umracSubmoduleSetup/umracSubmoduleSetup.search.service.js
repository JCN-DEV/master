'use strict';

angular.module('stepApp')
    .factory('UmracSubmoduleSetupSearch', function ($resource) {
        return $resource('api/_search/umracSubmoduleSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
