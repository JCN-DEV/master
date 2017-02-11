'use strict';

angular.module('stepApp')
    .factory('DlUllimConfigSearch', function ($resource) {
        return $resource('api/_search/dlUllimConfigs/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
