'use strict';

describe('DlBookRequisition Detail Controller', function() {
    var $scope, $rootScope;
    var MockEntity, MockDlBookRequisition, MockDlContTypeSet, MockDlContCatSet, MockDlContSCatSet;
    var createController;

    beforeEach(inject(function($injector) {
        $rootScope = $injector.get('$rootScope');
        $scope = $rootScope.$new();
        MockEntity = jasmine.createSpy('MockEntity');
        MockDlBookRequisition = jasmine.createSpy('MockDlBookRequisition');
        MockDlContTypeSet = jasmine.createSpy('MockDlContTypeSet');
        MockDlContCatSet = jasmine.createSpy('MockDlContCatSet');
        MockDlContSCatSet = jasmine.createSpy('MockDlContSCatSet');
        

        var locals = {
            '$scope': $scope,
            '$rootScope': $rootScope,
            'entity': MockEntity ,
            'DlBookRequisition': MockDlBookRequisition,
            'DlContTypeSet': MockDlContTypeSet,
            'DlContCatSet': MockDlContCatSet,
            'DlContSCatSet': MockDlContSCatSet
        };
        createController = function() {
            $injector.get('$controller')("DlBookRequisitionDetailController", locals);
        };
    }));


    describe('Root Scope Listening', function() {
        it('Unregisters root scope listener upon scope destruction', function() {
            var eventType = 'stepApp:dlBookRequisitionUpdate';

            createController();
            expect($rootScope.$$listenerCount[eventType]).toEqual(1);

            $scope.$destroy();
            expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
        });
    });
});
