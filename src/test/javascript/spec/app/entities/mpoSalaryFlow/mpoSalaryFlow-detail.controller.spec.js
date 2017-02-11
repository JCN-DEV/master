'use strict';

describe('MpoSalaryFlow Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockMpoSalaryFlow;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockMpoSalaryFlow = jasmine.createSpy('MockMpoSalaryFlow');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'MpoSalaryFlow': MockMpoSalaryFlow
        };
        createController = function() {
            $injector.get('$controller')("MpoSalaryFlowDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:mpoSalaryFlowUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
