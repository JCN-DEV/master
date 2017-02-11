'use strict';

angular.module('stepApp')
    .factory('TrainingRequisitionFormSearch', function ($resource) {
        return $resource('api/_search/trainingRequisitionForms/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
