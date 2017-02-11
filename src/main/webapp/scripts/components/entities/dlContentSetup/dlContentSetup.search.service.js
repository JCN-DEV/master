'use strict';

angular.module('stepApp')
    .factory('DlContentSetupSearch', function ($resource) {
        return $resource('api/_search/dlContentSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
