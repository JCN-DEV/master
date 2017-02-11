'use strict';

describe('InstEmplPayscaleHist Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockInstEmplPayscaleHist, MockInstEmployee, MockPayScale;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockInstEmplPayscaleHist = jasmine.createSpy('MockInstEmplPayscaleHist');
        MockInstEmployee = jasmine.createSpy('MockInstEmployee');
        MockPayScale = jasmine.createSpy('MockPayScale');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'InstEmplPayscaleHist': MockInstEmplPayscaleHist,
            'InstEmployee': MockInstEmployee,
            'PayScale': MockPayScale
        };
        createController = function() {
            $injector.get('$controller')("InstEmplPayscaleHistDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:instEmplPayscaleHistUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
