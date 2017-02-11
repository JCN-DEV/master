'use strict';

angular.module('stepApp')
    .factory('MiscConfigSetupSearch', function ($resource) {
        return $resource('api/_search/miscConfigSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
