'use strict';

angular.module('stepApp')
    .factory('HrDesignationHeadSetupSearch', function ($resource) {
        return $resource('api/_search/hrDesignationHeadSetups/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
