'use strict';

angular.module('stepApp')
    .factory('AlmWeekendConfigurationSearch', function ($resource) {
        return $resource('api/_search/almWeekendConfigurations/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
