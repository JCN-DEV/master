'use strict';

angular.module('stepApp')
    .factory('AlmLeaveRuleSearch', function ($resource) {
        return $resource('api/_search/almLeaveRules/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
